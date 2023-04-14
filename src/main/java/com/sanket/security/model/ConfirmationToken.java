package com.sanket.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;

import static com.sanket.security.common.constant.Constants.TOKEN_EXPIRATION;

@Entity
@Getter
@Setter
@Table(name = "confirmation_token")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenId;
    private Date createdDate;
    private Date expirationDate;
    private String confirmationToken;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        expirationDate = new Date(createdDate.getTime() + TOKEN_EXPIRATION);
        confirmationToken = UUID.randomUUID().toString();
    }
}
