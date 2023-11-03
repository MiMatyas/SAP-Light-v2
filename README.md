# SAP-Light-v2
Aplikace pro správu skladu

Použité technologie:

1   spring-boot

    a.  starter-web
    b.  starter-security
    c.  starter-data-jpa
    
2.  postgresql

3.  mapstruct
   
4.  swagger
   

Jedná se o backend aplikace pro správu skladu, jako frontend slouží swagger.
V aplikaci existují 4 role z nichž každá má přístup k:

1.  SuperAdministrátor

    a.  Má přístup ke všemu

3.  administrátor

    a. Manipulovat se zbožím všemi CRUD operacemi
    b. Vytvářet objednávky pro naskladnění zboží

5.  Zákazník

    a.  Vytvořit doručovací adresu
    b.  Vytvořit objednávku
    c.  Číst svoje adresy
    d.  Číst dvoje objednávky

7.  Operátor

    a.  Náhled všech objednávek
    b.  Příjem objednávky která je příjmová
    c.  Odeslání objednávky zákazníkovy
