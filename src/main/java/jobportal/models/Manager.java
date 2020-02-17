/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jobportal.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Trida objektu Manager
 */

@Entity
@Table(name = "managers")
public class Manager extends Person {

    @Column(name = "hire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @Column(name = "address")
    @NotNull(message = "Set contractors address")
    private String address;

    @Column(name = "city")
    @NotNull(message = "Set contractors address")
    private String city;

    @Column(name = "telephone")
    @NotNull
    @Min(value = 100000000, message = "Set real phone number")
    @Max(value = 999999999, message = "Set real phone number")
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamLeading;

    // constructors
    public Manager() {}

    public Manager(LocalDate hireDate, @NotNull(message = "Set contractors address") String address, @NotNull(message = "Set contractors address") String city, @NotNull @Min(value = 100000000, message = "Set real phone number") @Max(value = 999999999, message = "Set real phone number") String telephone, Team teamLeading) {
        this.hireDate = hireDate;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.teamLeading = teamLeading;
    }

    @Transient

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Team getTeamLeading() {
        return teamLeading;
    }

    public void setTeamLeading(Team teamLeading) {
        this.teamLeading = teamLeading;
    }

}