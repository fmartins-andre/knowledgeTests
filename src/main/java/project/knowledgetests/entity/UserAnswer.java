package project.knowledgetests.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Question questionReference;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    private Double scoreByOwnUser;

    private Double score;
}
