package project.knowledgetests.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_REFERENCE_ID", "USER_ID", "CREATION_DATE"})})
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, name = "QUESTION_REFERENCE_ID")
    private Question questionReference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, name = "USER_ID")
    private User user;

    @Column(nullable = false, updatable = false, length = 4096)
    private String question;

    @Column(nullable = false, length = 2048)
    private String answer;

    private Byte scoreByOwnUser;

    private Byte score;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "CREATION_DATE")
    private LocalDate creationDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;
}
