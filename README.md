
# X-Wing Lib

![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

A Java library for modeling ships and upgrades from the X-Wing miniatures game. Data used to populate this library is pulled
from the fantastic data in Guidokessel's [Xwing-data2](https://github.com/guidokessels/xwing-data2) repository. The library
is intended to be fairly lightweight and provides only high-level selection criteria for filtering on ships and upgrades. 

Currently, this library is in a pre-release state (v0.0.1). Any or all of this library is subject to change prior to 
reaching a stable release version.

### Usage

The Initializer class is responsible for cloning the xwing-data2 repository and deserializing the ships and upgrades.
The result of initialization is a ShipProducer (this needs a better name as it includes upgrades, but this is what it is for now).
The ShipProducer provides methods for getting lists of ships and upgrades.

Example:

```Java
import com.tcashcroft.xwinglib.exception.XwingLibInitializationException;
import com.tcashcroft.xwinglib.Initializer;
import com.tcashcroft.xwinglib.ShipProducer;

class Example {
  public void getObjects() throws XwingLibInitializationException {
    Initializer initializer = new Initializer(URI.create("https://github.com/guidokessels/xwing-data2"));
    ShipProducer shipProducer = initializer.getShipProducer();

    List<Ship> ships = shipProducer.getShips();
    List<Upgrade> upgrades = shipProducer.getUpgrades();
  }
}
```

### Disclaimer
All references to Star Wars characters and properties are the property of Disney and LucasFilm. All references to the Star War X-Wing Miniatures game are the property of 
Asmodee and whatever studio they have most recently assigned the X-Wing Miniatures Game to be a part of (Atomic Mass Games at the time of writing).

### License
[Apache v2](LICENSE)
