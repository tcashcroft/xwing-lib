package com.tcashcroft.xwinglib.model;

import lombok.Data;

import java.util.List;

/**
 * Represents The Force. A bit pretentious, I know.
 */
@Data
public class Force {
  private int value;
  private int recovers;
  private List<String> side;
}
