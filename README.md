# RozpoznavaniCisel

#KIV/UIR - Semestralní prace pro ak. rok 2015/16

##Klasifikace ručně psanych cislic

Ve zvolenem programovacím jazyce navrhněte a implementujte program, ktery bude
schopen klasifikovat ručně psane číslice 0 - 9. Při řešení budou splněny nasledující podmínky:

- vytvoření trenovacích / testovacích dat pro učení / testovaní systemu

-- Každy student napíše alespoň jednu sadu cifer (pro tvorbu cifer použijte do-
danou mřížku). Tyto budou dale oskenovany a převedeny do textoveho modu
formatu pgm (rastr o velikosti 128 x 128 bodů nakreslenych v 256 odstínech
šedi). Vytvořena data budou uložena na sdíleny disk, kde bude při ukladaní
dodržena nasledující konvence (osobní číslo = adresař pro data každeho stu-
denta, v něm založit podadresaře 0 - 9, kde budou uloženy reprezentace jednot-
livych cifer v uvedenem formatu).

- implementujte alespoň tři různe algoritmy (z přednašek i vlastní) pro tvorbu příznaků
reprezentující číslice

- implementujte alespoň dva různe klasifikační algoritmy (vlastní implementace, kla-
sifikace bude s učitelem, např. klasifikator s min. vzdaleností)

- funkčnost programu bude nasledující:
-- spuštění s parametry: trenovací množina, testovací množina, parametrizační algoritmus,
klasifikační algoritmus, nazev modelu
program natrenuje klasifikator na dane trenovací množině, použije zadany pa-
rametrizační/klasifikační algoritmus, zaroveň vyhodnotí uspěšnost klasifikace a
natrenovany model uloží do souboru pro pozdější použití (např. s GUI).
-- spuštění s jedním parametrem = nazev modelu : program se spustí s jedno-
duchym GUI a uloženym klasifikačním modelem. Program umožní klasifikovat
cifry napsane v GUI pomocí myši.

- ohodnot'te kvalitu klasifikatoru na vytvořenych datech, použijte metriku přesnost
(accuracy). Otestujte všechny konfigurace klasifikatorů (tedy celkem 6 vysledků).
Bonusove ukoly:

- vyzkoušejte již nějakou hotovou implementaci klasifikatoru (Weka, apod.) a vysledky
srovnejte s Vaší implementací

- vyzkoušejte klasifikaci bez učitele (např. k-means) a vysledky porovnejte s vysledky
klasifikací s učitelem
