/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.LocalDate;
import java.time.Period;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Supplier;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 
 * @author Asus
 */

public class PenghitungUmurHelper {
  // Menghitung umur secara detail (tahun, bulan, hari)
    public String hitungUmurDetail(LocalDate lahir, LocalDate sekarang) {
        Period period = Period.between(lahir, sekarang);
        return period.getYears() + " tahun, " + period.getMonths() + " bulan, " + period.getDays() + " hari";
    }

    // Menghitung hari ulang tahun berikutnya
    public LocalDate hariUlangTahunBerikutnya(LocalDate lahir, LocalDate sekarang) {
        LocalDate ulangTahunBerikutnya = lahir.withYear(sekarang.getYear());
        if (!ulangTahunBerikutnya.isAfter(sekarang)) {
            ulangTahunBerikutnya = ulangTahunBerikutnya.plusYears(1);
        }
        return ulangTahunBerikutnya;
    }

    // Menerjemahkan teks hari ke bahasa Indonesia
    public String getDayOfWeekInIndonesian(LocalDate date) {
        switch (date.getDayOfWeek()) {
            case MONDAY:
                return "Senin";
            case TUESDAY:
                return "Selasa";
            case WEDNESDAY:
                return "Rabu";
            case THURSDAY:
                return "Kamis";
            case FRIDAY:
                return "Jumat";
            case SATURDAY:
                return "Sabtu";
            case SUNDAY:
                return "Minggu";
            default:
                return "";
        }
    
    }
// Mendapatkan peristiwa penting secara baris per baris
public void getPeristiwaBarisPerBaris(LocalDate tanggal, JTextArea
txtAreaPeristiwa, Supplier<Boolean> shouldStop) {
try {
// Periksa jika thread seharusnya dihentikan sebelum dimulai
if (shouldStop.get()) {
return;
}

String urlString = "https://byabbe.se/on-this-day/" +
tanggal.getMonthValue() + "/" + tanggal.getDayOfMonth() + "/events.json";
URL url = new URL(urlString);
HttpURLConnection conn = (HttpURLConnection)
url.openConnection();
conn.setRequestMethod("GET");
conn.setRequestProperty("User-Agent", "Mozilla/5.0");
int responseCode = conn.getResponseCode();
if (responseCode != 200) {
throw new Exception("HTTP response code: " + responseCode +
". Silakan coba lagi nanti atau cek koneksi internet.");
}
BufferedReader in = new BufferedReader(new
InputStreamReader(conn.getInputStream()));
String inputLine;
StringBuilder content = new StringBuilder();
while ((inputLine = in.readLine()) != null) {
// Periksa jika thread seharusnya dihentikan saat membacadata
if (shouldStop.get()) {
in.close();
conn.disconnect();
javax.swing.SwingUtilities.invokeLater(() ->
txtAreaPeristiwa.setText("Pengambilan data dibatalkan.\n"));
return;
}
content.append(inputLine);
}
in.close();
conn.disconnect();
JSONObject json = new JSONObject(content.toString());
JSONArray events = json.getJSONArray("events");
for (int i = 0; i < events.length(); i++) {
// Periksa jika thread seharusnya dihentikan sebelummemproses data
if (shouldStop.get()) {
javax.swing.SwingUtilities.invokeLater(() ->
txtAreaPeristiwa.setText("Pengambilan data dibatalkan.\n"));
return;
}
JSONObject event = events.getJSONObject(i);
String year = event.getString("year");
String description = event.getString("description");
String peristiwa = year + ": " + description;

javax.swing.SwingUtilities.invokeLater(() ->
txtAreaPeristiwa.append(peristiwa + "\n"));
}
if (events.length() == 0) {
javax.swing.SwingUtilities.invokeLater(() ->
txtAreaPeristiwa.setText("Tidak ada peristiwa penting yang ditemukan padatanggal ini."));
}
} catch (Exception e) {
javax.swing.SwingUtilities.invokeLater(() ->
txtAreaPeristiwa.setText("Gagal mendapatkan data peristiwa: " +
e.getMessage()));
}
}
void getPeristiwaBarisPerBaris(JTextField txtHariUlangTahunBerikutnya, JTextArea txtAreaPeristiwa, Supplier<Boolean> supplier) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}   


