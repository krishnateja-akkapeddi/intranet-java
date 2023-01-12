package com.intr.vgr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long messageId;
    @NotEmpty
    private String message;
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    private User receiver;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

}