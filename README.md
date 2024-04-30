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

## Proje Yolculuğu

Projenin detaylarına ilişkin kapsamlı bir bakış için aşağıdaki resme tıklayıp videoyu izleyebilirsiniz.

[<img width ="300px" src="https://taplink.st/a/0/2/4/2/66bd8f.png?1">](https://youtu.be/xjMg8fm_piQ)

### Başlangıç

Projeye başlarken ilk olarak giriş sayfasını ve admin kullanıcının göreceği arayüzü oluşturdum. Bu adımın ardından backend kısmını da hazırladım.

<img width="300px" src="/images/Screenshot%202024-04-29%20at%2020.56.36.png" /><img width="600px" src="/images/Screenshot%202024-04-29%20at%2020.56.49.png" />

### İlerleme

Daha sonra sırasıyla otel, oda ve rezervasyon arayüzlerini ekledim ve her birinin backend kısmını yazdım.

<img width="600px" src="/images/Screenshot%202024-04-29%20at%2020.57.23.png" />
<img width="600px" src="/images/Screenshot%202024-04-29%20at%2020.58.04.png" />
<img width="600px" src="/images/Screenshot%202024-04-29%20at%2021.35.38.png" />

### Sonuç

Bu adımların ardışık olarak ilerlemesi, daha az hata ile karşılaşmamı ve temiz kod yazmamı sağladı. Projede SOLID prensiplerine uygun olmaya çalıştım ve her arayüzde yeni bir özellik ekleyerek daha fazla deneyim kazanmaya odaklandım.

## Teknik Detaylar

- **Veritabanı:** PostgreSQL 16 kullanıldı.
- **Arayüz:** Java Swing kullanıldı.
- **Java SDK Sürüm:** Java SDK 21 sürümü kullanıldı.
