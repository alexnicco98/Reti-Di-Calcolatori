# Reti-di-Calcolatori
Istruzioni sottomissione assignments
Corso di laboratorio di reti

October 8, 2019

In questo documento sono raccolte tutte le linee guida da seguire per ef-
fettuare le consegne degli esercizi. Non `e necessario riscrivere il codice per gli
esercizi 1 (Calcolo pi greco), 2 (Uﬃcio postale) e, per il corso B, 3 (Reparto)
se sono gi`a stati consegnati, ma dovrete attenervi alle regole per i prossimi
assegnamenti.
• Struttura del codice: Il codice deve essere ben commentato, special-
mente nelle sezioni dove sono state fatta delle scelte implementative che
erano lasciate libere nel testo dell’esercizio.
Inoltre, il codice deve eseguire senza la richiesta di input da parte di un
utente. Nel caso in cui c’`e la necessit`a di passare dei valori al codice (es-
empio: la precisione richiesta per il calcolo del pi greco), dovrete farlo
tramite gli argomenti del main. Argomenti al main tramite riga di co-
mando possono essere passati nella maniera canonica, mentre se utiliz-
zate un ambiente di sviluppo potete passarli personalizzando le conﬁgu-
razioni di esecuzione: Run → RunConfigurations... → Arguments →
P rogramArguments. Se il codice richiede che siano passati argomenti al
main inserite anche una riga commentata sopra al main in cui speciﬁcate
quali argomenti, e in che formato, sono richiesti.
Per esempio, il commento al metodo main dell’esercizio Calcolo PiGreco
pu`o essere del tipo:

public class PiGreco implements Runnable {

public static void main(String[] args) {
// MainClass accuracy mseconds
// accuracy (double) accuracy in decimale
// mseconds (int) tempo in millisecondi
...
}

}

Inﬁne, per capire se il codice sta eﬀettivamente eseguendo ci`o che era
richiesto nell’esercizio, dovrete mettere alcune stampe. Cercate di trovare
un buon equilibrio: non mettete una stampa ogni 2 righe di codice, ma
evitate di far stampare solo un OK alla ﬁne dell’esecuzione.
• File da consegnare: nelle sottomissioni moodle, ma anche per quanto
riguarda i ricevimenti elettronici, i ﬁle da consegnare sono i sorgenti e gli
input signiﬁcativi.
Unico vincolo sui sorgenti `e che la classe che contiene il metodo main si
chiami MainClass.java; per le altre classi non ci sono vincoli, ma nomi
mnemonici sono ovviamente apprezzati.
I parametri passati al main, se previsti, devono essere inseriti in ﬁle plain
text il cui nome inizia per input: una buona regola `e di nominarli usando
numeri progressivi, ovvero input1.txt, input2.txt, . . . inputn.txt.
Ogni ﬁle di input, deve contenere i parametri da passare al main separati
da spazio. In genere un solo ﬁle di input `e suﬃciente per controllare se il
codice che avete consegnato `e funzionante.
Per esempio, di seguito `e riportato il contenuto del ﬁle input1.txt conte-
nente i due parametri (separati da spazio) da passare al main dell’esercizio
Calcolo PiGreco.

0.01 5000

Tutti i ﬁle da consegnare (sorgenti .java e ﬁles di input .txt) devono
essere caricati su moodle in un unico archivio compresso. Non `e richiesta
una particolare estensione, ma `e fortemente consigliato attenersi alle piu`
comuni: .zip e .tar (.gz, .rar e .7z come seconde scelte).
La cartella compressa contenente la soluzione dell’esercizio deve essere
chiamata seguendo il formato: MATRICOLA COGNOME NOME.zip. Per esem-
pio, lo studente Mario Rossi con matricola `e 012345 deve sottomettere la
soluzione in una cartella compressa nominata 012345 ROSSI MARIO.zip.
Per creare archivi zip potete usare il comando zip da terminale su linux,
oppure una qualsiasi delle mille utility che ci sono disponibili in rete1.
.
.
.
.
.
.


In data 15/12/19 consegnato ultimo assignment
   
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠙⢿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢁⠒⠀⢀⡔⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⢹⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠁⠀⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿
⣿⣿⣿⣿⣿⠿⠟⠛⠛⠓⢄⡀⠀⢂⠀⠀⠀⠀⠠⡀⡌⠀⠀⠀⠀⠀⠀⢰⣿⣿
⣿⣿⣿⣿⢁⠄⢀⠀⠀⠀⠀⠈⠢⡄⠁⠀⢀⣀⠀⠘⠀⠀⠀⠀⡀⠀⣠⣾⣿⣿
⣿⣿⣿⣿⣯⡂⠀⠀⠀⠀⡀⠀⠀⠈⠢⡀⠀⠀⠀⠀⠀⡠⠔⡞⣡⣴⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣦⡠⠈⢦⡀⠀⠀⠀⠀⠱⡀⠀⠀⠀⠀⢄⣾⣾⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣆⢢⣿⣄⡀⠀⠀⠀⢐⠀⠀⡀⢐⠆⣹⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣿⣄⡄⠀⠈⠀⠀⠁⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⡁⢘⣿⣿⣿⣿⣿⡀⢀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⡸⣿⣿⠏⠻⠟⠀⡌⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⠟⡋⠀⠀⠀⠠⠐⠉⠀⠀⢀⠨⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⠁⠀⠆⠀⠀⠀⡀⠄⠀⠀⠈⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⠏⠀⠀⢠⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠛⠻⠿⣿⣿⣿⣿
⣿⣿⡿⠛⠉⠀⠀⠀⠀⠈⠀⠀⠀⠀⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣿
⣿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻
