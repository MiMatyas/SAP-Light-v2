# SAP-Light-v2
Aplikace pro správu skladu

Použité technologie:
1   spring-boot
    a.  starter-web
    b.  starter-security
    c.  starter-data-jpa
2.  postgresql
3.  mapstruct
4   swagger

Jedná se o backend aplikace pro správu skladu, jako frontend slouží swagger.
V aplikaci existují 4 role z nichž každá má přístup k:

1.  SuperAdministrítor
    a.  Má přístup ke všemu

2.  administrátor
    a. manipulovat se zbožím všemi CRUD operacemi
    b. vytvářet objednávky pro naskladnění zboží

3.  Zákazník
    a.  Vytvořit doručovací adresu
    b.  Vytvořit objednávku
    c.  Číst svoje adresy
    d.  Číst dvoje objednávky

4.  Operátor
    a.  Náhled všech objednávek
    b.  příjem objednávky která je příjmová
    c.  odeslání objednávky zákazníkovy
