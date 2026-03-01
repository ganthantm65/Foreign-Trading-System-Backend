package com.example.Foreign.Trading.System.Repository;

import com.example.Foreign.Trading.System.Model.Banker;
import com.example.Foreign.Trading.System.Model.Exporter;
import com.example.Foreign.Trading.System.Model.Importer;
import com.example.Foreign.Trading.System.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users,Integer> {

    // Importer
    @Query("SELECT u FROM Users u WHERE u.email = :email AND TYPE(u) = Importer")
    Importer findImporterByEmail(@Param("email") String email);

    // Exporter
    @Query("SELECT u FROM Users u WHERE u.email = :email AND TYPE(u) = Exporter")
    Exporter findExporterByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.userName= :userName AND companyName = :companyName AND TYPE(u) = Exporter")
    Exporter findExporterByNameAndCompany(@Param("userName") String userName,@Param("companyName") String companyName);

    // Banker
    @Query("SELECT u FROM Users u WHERE u.email = :email AND TYPE(u) = Banker")
    Banker findBankerByEmail(@Param("email") String email);
}