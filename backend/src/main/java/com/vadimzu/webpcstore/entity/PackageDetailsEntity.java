/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ManyToAny;

/**
 *
 * @author vadimzubchenko
 */
@Entity
@Table(name = "packageDetails")
//to fix infinity exception caused by OneToMany and ManyToOne
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "packageDetailsID")
public class PackageDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageDetailsID;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private PartEntity part;

    public PackageDetailsEntity() {
    }

    public Long getPackageDetailsID() {
        return packageDetailsID;
    }

    public void setPackageDetailsID(Long packageDetailsID) {
        this.packageDetailsID = packageDetailsID;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public PartEntity getPartEntity() {
        return part;
    }

    public void setPartEntity(PartEntity party) {
        this.part = part;
    }

}
