package com.talniv.intuit.testutils;

import com.talniv.intuit.data.Player;
import lombok.Data;

import java.util.List;

@Data
public class GetAllResponse {
    List<Player> content;
}
