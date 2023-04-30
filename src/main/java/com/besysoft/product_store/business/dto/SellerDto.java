package com.besysoft.product_store.business.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SellerDto implements Serializable {

    private Long id;

    @Size(max = 30)
    @NotBlank(message = "Este campo no puede estar vacío")
    private String name;

    private BigDecimal salary;

    private BigDecimal commission;

    public SellerDto() {
    }

    public SellerDto(Long id, String name, BigDecimal salary, BigDecimal commission) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.commission = commission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerDto sellerDto = (SellerDto) o;
        return Objects.equals(id, sellerDto.id) && Objects.equals(name, sellerDto.name)
                && Objects.equals(salary, sellerDto.salary)
                && Objects.equals(commission, sellerDto.commission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary, commission);
    }
}
