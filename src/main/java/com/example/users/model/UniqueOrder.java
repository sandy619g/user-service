package com.example.users.model;

import lombok.*;
import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UniqueOrder implements Serializable {
    public String id;
    public String login;
}