//import bankprojekt.verarbeitung.Aktie;
//import bankprojekt.verarbeitung.Girokonto;
//import bankprojekt.verarbeitung.Konto;
//
//import java.util.concurrent.*;
//
///**
// * Testprogramm fuer Aktien. Es werden Aktien angelegt, gekauft und verkauft.
// */
//public class AktienSpielereien {
//    /**
//     * Testprogramm fuer Aktien. Es werden Aktien angelegt, gekauft und verkauft.
//     *
//     * @param args
//     * @throws ExecutionException
//     * @throws InterruptedException
//     */
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//
//        Aktie cola = new Aktie("Cola", "WKN01", 100.01);
//        Aktie pepsi = new Aktie("Pepsi", "WKN02", 120.01);
//        Aktie fanta = new Aktie("Fanta", "WKN03", 80.01);
//
//        Konto konto = new Girokonto();
//        konto.einzahlen(5000);
//
//        executorService.scheduleAtFixedRate(() -> {
//            System.out.println("------------------------------------");
//            System.out.println("Aktueller Kurs von Cola: " + cola.getKurs());
//            System.out.println("Aktueller Kurs von Pepsi: " + pepsi.getKurs());
//            System.out.println("Aktueller Kurs von Fanta: " + fanta.getKurs());
//            System.out.println("------------------------------------");
//        }, 0, 1, TimeUnit.SECONDS);
//
//
//        Future<Double> kaufCola = konto.kaufauftrag(cola, 2, 101.0);
//        System.out.println("Kaufe Cola für: " + kaufCola.get());
//        Future<Double> verkaufCola = konto.verkaufauftrag("WKN01", 102);
//        System.out.println("Verkaufe Cola für: " + verkaufCola.get());
//
//        Future<Double> kaufPepsi = konto.kaufauftrag(pepsi, 2, 121.0);
//        System.out.println("Kaufe Pepsi für: " + kaufPepsi.get());
//        Future<Double> verkaufPepsi = konto.verkaufauftrag("WKN02", 110);
//        System.out.println("Verkaufe Pepsi für: " + verkaufPepsi.get());
//
//        Future<Double> kaufFanta = konto.kaufauftrag(fanta, 2, 79.0);
//        System.out.println("Kaufe Fanta für: " + kaufFanta.get());
//        Future<Double> verkaufFanta = konto.verkaufauftrag("WKN03", 85);
//        System.out.println("Verkaufe Fanta für: " + verkaufFanta.get());
//
//
//        cola.stopKurs();
//        pepsi.stopKurs();
//        fanta.stopKurs();
//        executorService.shutdown();
//    }
//}