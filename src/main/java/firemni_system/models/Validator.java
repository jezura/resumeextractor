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
package firemni_system.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Trida objektu Validator
 */

@Entity
@Table(name = "validators")
public class Validator extends Person {

    @Column(name = "hire_date")
    @NotNull(message = "Set hire date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @Column(name = "address")
    @NotEmpty(message = "Set contractors address")
    private String address;

    @Column(name = "city")
    @NotEmpty(message = "Set contractors address")
    private String city;

    @Column(name = "telephone")
    @Min(value = 100000000, message = "Set real phone number, format: xxxxxxxxx")
    @Max(value = 999999999)
    private String telephone;

    @ManyToOne
    @NotNull(message =  "Set team")
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // constructors
    public Validator() {}

    public Validator(@NotNull(message = "Set hire date") LocalDate hireDate, @NotEmpty(message = "Set contractors address") String address, @NotEmpty(message = "Set contractors address") String city,  @Min(value = 100000000, message = "Set real phone number") @Max(value = 999999999, message = "Set real phone number") String telephone, @NotNull(message = "Set team") Team team, Post post) {
        this.hireDate = hireDate;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.team = team;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
