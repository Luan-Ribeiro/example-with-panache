package org.quarkus.test.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@RegisterForReflection
@Entity
@Table(name = "car")
@Schema(example = """
    {
      "name": "carro de domingo",
      "color": "red",
      "value": 2000000
    }
    """)
public class Car extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonInclude(value = Include.NON_EMPTY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @NotBlank
    private String color;
    @NotNull
    private BigInteger value;

    public static Uni<Long> deleteById(Long id){
        return delete("id",id);
    }

    public static Uni<Car> getByName(String name){
        return find("name",name).firstResult();
    }
}
