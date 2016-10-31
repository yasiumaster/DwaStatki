# DwaStatki

![alt tag](http://oi64.tinypic.com/2u5du0g.jpg)

Konfiguracja:

1. Pobierz biblioteki: Slick2d, lwjgl, Kryonet. Umiesc je w /lib w obu projektach.
2. Skonfiguruj projekt jako dwa osobne: DwaStatki - jako serwer, DwaStatkiClient - jako klient.
3. Skonfiguruj Java Build Path dodajac pobrane biblioteki.
4. Dla JRE System Library dodaj Native library location (lib/natives/[OS]) w projekcie klienta.

Uruchamianie:

1. Uruchom projekt sewera z klasy game.Game.
2. Uruchom projekt klienta z klasy game.Game.
