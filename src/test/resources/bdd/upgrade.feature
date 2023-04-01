
  Feature: Upgrade-level features of the X-Wing library

    Scenario Outline: Get a double-sided upgrade and validate it
      Given a componentProducer for upgrades
      When I ask for a double sided <upgradeName> card
      Then both sides are valid
      Examples:
      | upgradeName |
      | "servomotorsfoils" |

    Scenario Outline:
      Given a componentProducer for upgrades
      When I ask for an upgrade <upgradeName> that grants stats
      Then stats array is populated
      Examples:
      | upgradeName |
      | "delta7b" |