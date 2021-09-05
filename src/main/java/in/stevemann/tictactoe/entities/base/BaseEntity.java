package in.stevemann.tictactoe.entities.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String code = UUID.randomUUID().toString();

    @Version
    private Integer version;

    private String name;

    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    private boolean enabled = true;
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
