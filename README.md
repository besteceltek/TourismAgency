# Turizm Acente Sistemi

Bu proje, Patika Turizm Acentesi'nin günlük operasyonlarını dijitalleştirmeyi ve müşteri rezervasyon süreçlerini optimize etmeyi amaçlamaktadır. Şu anda projenin eksikleri aşağıda belirtilmiştir:

## Proje İsterleri

1. **Veritabanı:** Veritabanı tabloları aşağıdaki gibi oluşturulmalıdır:
    - `user`: admin ve acente çalışanı kullanıcı bilgilerini tutar.
    - `hotel`: otel bilgilerini tutar.
    - `season`: otel sezon kayıtlarını tutar.
    - `pension`: otel pansiyon tiplerini tutar.
    - `room`: otel odalarını ve özelliklerini tutar.
    - `reservation`: odaya yapılan rezervasyonları tutar.
    - `hotel_features`: otel özelliklerini tutar.

2. **Arayüz Tasarımı:** Java Swing kullanılmalıdır. Arayüz, kullanıcı dostu olmalı ve kendine özgü bir tasarıma sahip olmalıdır.

3. **Kullanıcı Yönetimi:** Admin panelinden kullanıcıları eklemek, düzenlemek ve silmek mümkün olmalıdır. Kullanıcıların rolleri (admin, personel) belirlenmelidir.

4. **Otel Yönetimi:** Otel ekleme ve listeleme işlevleri sağlanmalıdır. Otelin özellikleri, pansiyon tipleri ve dönemleri kaydedilmelidir.

5. **Dönem Yönetimi:** Otelin dönemleri belirlenmeli ve fiyatlandırmalar bu dönemlere göre yapılmalıdır.

6. **Oda Yönetimi:** Oda ekleme ve listeleme işlevleri sağlanmalıdır. Odaların fiyatlandırması ve özellikleri belirlenmelidir.

7. **Fiyatlandırma:** Odaların fiyatlandırması otelin dönemine, pansiyon tipine ve misafir sayısına göre otomatik olarak hesaplanmalıdır.

8. **Oda Arama ve Rezervasyon:** Acente çalışanları sisteme girdiği tarih aralığına, şehire veya otel adına göre oda arama yapabilmelidir. Rezervasyon işlemi tamamlandığında toplam fiyat otomatik olarak hesaplanmalı ve stok bilgisi güncellenmelidir.

## Projenin Yapımı

Proje ile ilgili detaylı bilgiler için aşağıdaki videoyu izleyebilirsiniz.








Projeye başlarken ilk önce giriş sayfasını ve admin kullanıcının göreceği arayüzü ve backendi hazırladım.
![Giriş Sayfası](/images/Screenshot%202024-04-29%20at%2020.56.36.png)
![Admin Arayüzü](/images/Screenshot%202024-04-29%20at%2020.56.49.png)
![Kullanıcı Ekleme Arayüzü](/images/Screenshot%202024-04-29%20at%2020.56.57.png)
![]()
![]()
![]()
![]()
![]()
![]()

## Teknik Detaylar

- **Veritabanı:** PostgreSQL 16 kullanıldı.
- **Arayüz:** Java Swing kullanıldı.
- **Java SDK Sürüm:** Java SDK 21 sürümü kullanıldı.
