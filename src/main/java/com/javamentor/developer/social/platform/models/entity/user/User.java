package com.javamentor.developer.social.platform.models.entity.user;

import com.javamentor.developer.social.platform.models.dto.users.MostPopularProfessionsInUsersDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;




@NamedNativeQuery(
        name = "MostPopularProfessionsInUsers",
        query = "select t.profession as title, round((100.00 * count(*)) / (select count(*) from users), 2) as rate \n" +
                "from users pt join users t on pt.user_id = t.user_id\n" +
                "                group by t.profession \n" +
                "                order by rate desc",
        resultSetMapping = "MostPopularProfessionsInUsersMapping"
)
@SqlResultSetMapping(
        name = "MostPopularProfessionsInUsersMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "title"),
                                @ColumnResult(name = "rate", type = double.class)
                        },
                        targetClass = MostPopularProfessionsInUsersDto.class
                )
        }
)


@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "users_seq")
    private Long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    //@NotNull
    private LocalDate dateOfBirth;

    @Column(name = "education")
    private String education;

    @Column
    private String aboutMe;


    @Column(name = "image")
    private String avatar;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profession")
    private String profession;

    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastRedactionDate;


    @Getter(AccessLevel.NONE)
    @Column(name = "is_enable")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isEnable = false;

    @Column(name = "city")
    private String city;

    @Column(name = "link_site")
    private String linkSite;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "status")
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Active.class)
    @JoinColumn(name = "active_id", nullable = false)
    private Active active;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Language.class)
    @JoinTable(name = "user_languages", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Audios.class)
    @JoinTable(name = "users_audios_collections", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_id"))
    private Set<Audios> audios;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Videos.class)
    @JoinTable(name = "users_videos_collections", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    private Set<Videos> videos;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = GroupChat.class)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_chat_id"))
    private Set<GroupChat> groupChats;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }

    @Override
    public String getUsername() {
         return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
