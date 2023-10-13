# MONETA
                     Úkol od MONETA

KROK:1 přejděte na cestu, kde se nachází Dockerfile
například /.../ticketing-system/

KROK 2: Chcete-li vytvořit obrázek, zadejte následující příkaz::
# Vytvořte obrázek

docker build -t ticketing-system .

KROK:3 vytvořte výchozí kontejner a spusťte image::
# Vytvořte kontejner a spusťte

docker run -t -p 8080:8080 ticketing-system:latest

Tím se aplikace spustí na portu 8080

# Chcete-li otevřít a zkontrolovat všechna rozhraní Rest Api

# Swagger odkaz:
http://localhost:8080/swagger-ui/index.html

# Databáze H2:
http://localhost:8080/h2-console/ --> Chcete-li zkontrolovat DATABÁZI
Pro přihlášení použijte JDBC URL -> jdbc:h2:mem:ts
Heslo není vyžadováno

# URL:
http://localhost:8080/ticket/generateTicket -> vygenerovat nový tiket
http://localhost:8080/ticket/currentWaitingTicket -> získá aktuální čekací lístek
http://localhost:8080/ticket/allTickets -> získejte všechny vstupenky
http://localhost:8080/ticket/lastActiveTicket -> získat poslední aktivní tiket


# Testovací případy mají pokrytí = 87 %