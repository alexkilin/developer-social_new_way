package com.javamentor.developer.social.platform.models.entity.token;

import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_verification_tokens")
public class VerificationToken {

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = 60)
    private String value;

    @Column(name = "expires_at", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime expirationDateTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(
            foreignKeyDefinition = "foreign key (id) references users on delete cascade"
    ))
    @PrimaryKeyJoinColumn
    private User user;

    protected VerificationToken(User user, String value) {
        this.id = user.getUserId();
        this.user = user;
        this.value = value;
        expirationDateTime = LocalDateTime.now().plusHours(1L);
    }

    public static class TokenGenerator {

        private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        public static VerificationToken getFor(User user) {
            String hash = passwordEncoder.encode(user.getEmail() + user.getPersistDate());
            // хэш начинается с последовательности $2a$10$... - ее пропускаем
            String tokenValue = hash.substring(hash.lastIndexOf('$') + 1).replaceAll("\\W", "-");
            return new VerificationToken(user, tokenValue);
        }
    }
}
