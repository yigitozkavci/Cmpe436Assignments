## There is a race, and RoadRunner finds it with tool TL:RS:LS
javac Race.java && rrrun -quiet -noxml -tool=TL:RS:LS Race

## There is a race but RoadRunner cannot find it with tool TL:RS:LS
javac NoRace.java && rrrun -quiet -noxml -tool=TL:RS:LS NoRace
