package com.example.Foreign.Trading.System.Service;

import com.example.Foreign.Trading.System.Exceptions.BalanceDeclinedException;
import com.example.Foreign.Trading.System.Model.Bank;
import com.example.Foreign.Trading.System.Model.BankAccount;
import com.example.Foreign.Trading.System.Model.DTO.ForexResponse;
import com.example.Foreign.Trading.System.Model.Trade;
import com.example.Foreign.Trading.System.Repository.BankAccountRepo;
import com.example.Foreign.Trading.System.Repository.BankRepo;
import com.example.Foreign.Trading.System.Repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BankService {

    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    private TradeRepository tradeRepository;

    private WebClient webClient = WebClient.create();

    @Transactional(rollbackFor = Exception.class)
    public void verifyAndTransferFunds(String importerAccNo, String exporterAccNo, double fund, int tradeId)
            throws BalanceDeclinedException {

        BankAccount importerAccount = findAccountByAccountId(importerAccNo);
        BankAccount exporterAccount = findAccountByAccountId(exporterAccNo);

        if (importerAccount == null || exporterAccount == null) {
            throw new RuntimeException("Invalid account details");
        }

        if (fund > importerAccount.getBalance()) {
            throw new BalanceDeclinedException("Balance is declined");
        }

        double rate = getForexRate(importerAccount.getCurrencyCode(), exporterAccount.getCurrencyCode());
        if (rate <= 0) {
            throw new RuntimeException("Invalid forex rate");
        }

        BigDecimal fundBD = BigDecimal.valueOf(fund);
        BigDecimal rateBD = BigDecimal.valueOf(rate);
        BigDecimal convertedAmountBD = fundBD.multiply(rateBD).setScale(2, RoundingMode.HALF_UP);

        BigDecimal importerNewBalance = BigDecimal.valueOf(importerAccount.getBalance()).subtract(fundBD);
        BigDecimal exporterNewBalance = BigDecimal.valueOf(exporterAccount.getBalance()).add(convertedAmountBD);

        importerAccount.setBalance(importerNewBalance.doubleValue());
        exporterAccount.setBalance(exporterNewBalance.doubleValue());

        bankAccountRepo.save(importerAccount);
        bankAccountRepo.save(exporterAccount);

        tradeRepository.updateTradeStatus(tradeId, Trade.Status.VERIFIED);

        System.out.println("Converted Amount Sent: " + convertedAmountBD.doubleValue());
    }

    public double getForexRate(String importerCurrency, String exporterCurrency) {
        String url = "https://api.forexrateapi.com/v1/latest"
                + "?api_key=8f67a5979fb228aed5feaa2830d7c751"
                + "&base=" + importerCurrency
                + "&currencies=" + exporterCurrency;

        ForexResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ForexResponse.class)
                .block();

        if (response == null || response.getRates() == null || !response.getRates().containsKey(exporterCurrency)) {
            throw new RuntimeException("Invalid forex API response");
        }

        return response.getRates().get(exporterCurrency);
    }

    public Bank findBankBySwiftCode(String swiftCode) {
        return bankRepo.findBankBySwiftCode(swiftCode);
    }

    public BankAccount findAccountByAccountId(String accNo) {
        return bankAccountRepo.findByAccNo(accNo);
    }
}