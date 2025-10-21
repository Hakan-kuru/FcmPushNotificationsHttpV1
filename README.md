# FCM Push Notifications HTTP v1

Bu proje, Firebase Cloud Messaging (FCM) HTTP v1 protokolünü kullanarak sunucu tarafından cihazlara anlık bildirim göndermeyi gösteren bir Android uygulamasıdır. Uygulama, kullanıcının bir uzak kullanıcı belirteci (remote token) girmesine ve ardından bu belirtece veya tüm kullanıcılara yayın (broadcast) yoluyla bildirim göndermesine olanak tanır.

## Özellikler

* **FCM Belirteci Girişi:** Uygulama başlangıcında kullanıcıdan, bildirim gönderilecek uzak cihazın FCM belirtecini girmesi istenir.
    * Kullanıcının kendi cihazının belirtecini kolayca kopyalaması için bir düğme mevcuttur.
    * [cite_start]Belirteç, `OutlinedTextField` kullanılarak girilir[cite: 9, 10].
* **Anlık Bildirim Gönderme:** Kullanıcı, girilen metni uzak kullanıcıya bildirim olarak gönderebilir.
    * Mesaj gönderme işlevi `onMessageSend` ile tetiklenir.
* **Yayın (Broadcast) Bildirimi:** Tüm kullanıcılara bildirim gönderme yeteneği vardır (sunucu tarafındaki konfigürasyon varsayılır).
    * Yayın gönderme işlevi `onMessageBroadcast` ile tetiklenir.
* **Retrofit Kullanımı:** Bildirimleri göndermek için bir sunucu API'si (`FcmApi`) ile iletişim kurmak üzere Retrofit kütüphanesi kullanılır.
    * API'ye mesaj göndermek için `/send` ve yayın yapmak için `/broadcast` uç noktaları tanımlıdır.
    * API'nin temel URL'si **`http://10.0.2.2:8080/`** olarak belirlenmiştir.
* **Bildirim İzni İstemi:** Android 13 (TIRAMISU) ve üzeri cihazlarda, `POST_NOTIFICATIONS` izni otomatik olarak istenir.
* **Firebase Mesajlaşma Servisi:** Gelen FCM mesajlarını ve yeni belirteç olaylarını ele alan `PushNotificationService` sınıfı kullanılır.
* [cite_start]**Compose Arayüzü:** Kullanıcı arayüzü Jetpack Compose ile oluşturulmuştur[cite: 1, 34].

## Kullanılan Teknolojiler ve Kütüphaneler

* Kotlin
* Jetpack Compose (UI)
* [cite_start]Firebase Messaging (FCM) [cite: 34]
* [cite_start]Retrofit (HTTP İstemcisi) [cite: 34]
* [cite_start]Moshi Converter (JSON serileştirme) [cite: 34]
* Kotlin Coroutines (`viewModelScope`)

## Kurulum ve Çalıştırma

1.  **Gereksinimler:**
    * Android Studio
    * [cite_start]Java 11 veya üzeri [cite: 33]
    * FCM sunucu entegrasyonuna (örneğin, bir sunucu uç noktası) sahip olmak. Bu proje, API'nin `http://10.0.2.2:8080/` adresinde çalıştığını varsaymaktadır.
2.  **Firebase Entegrasyonu:**
    * Projenizi Firebase Console'da kurun.
    * `google-services.json` dosyasını Android projenizin `app/` dizinine yerleştirin.
    * Projenin build dosyalarında `com.google.gms.google-services` eklentisinin uygulandığından emin olun.
3.  **İzinler:**
    * Uygulama, `AndroidManifest.xml` dosyasında `android.permission.INTERNET` ve `android.permission.POST_NOTIFICATIONS` izinlerini kullanır.
    * [cite_start]Gelen FCM mesajlarını dinlemek için `PushNotificationService` servisi beyan edilmiştir[cite: 31].
4.  **Uygulamayı Başlatma:**
    * Uygulamayı bir cihaza veya emülatöre kurup çalıştırın.
    * [cite_start]Uygulama, başlangıçta `EnterTokenDialog` ile açılır[cite: 21].
    * [cite_start]Kendi cihazınızın FCM belirtecini kopyalamak için diyalogdaki **copy token** düğmesini kullanabilirsiniz[cite: 15].
    * [cite_start]Uzak belirteci girdikten sonra **Submit** ile sohbet ekranına geçin[cite: 21].
5.  **Bildirim Gönderme:**
    * Metin alanına mesajınızı girin.
    * [cite_start]Sağ alttaki **Gönder** (${\text{Icons.Default.Send}}$) düğmesiyle doğrudan girilen belirtece (`remoteToken`) bildirim gönderin[cite: 5, 26].
    * [cite_start]**Yayınla** (${\text{Icons.Default.Share}}$) düğmesiyle tüm kullanıcılara bildirim gönderin (`isBroadcast = true`)[cite: 4, 26].
    * [cite_start]Başarılı bir gönderimden sonra mesaj metin alanı temizlenir[cite: 27].

## Ekran Görüntüleri

### 1. Token Giriş/Kopyalama Diyaloğu

Kullanıcının uzak cihazın belirtecini girdiği ve kendi belirtecini kopyalayabildiği ekran.

![Token Giriş Diyaloğu](fcmPushNotificationsHttpV1\Screenshot_20251021_191340.png)

### 2. Sohbet Ekranı (Mesaj Gönderme/Yayınlama)

Kullanıcının mesaj yazıp gönderebildiği veya yayınlayabildiği ana ekran.

![Sohbet Ekranı](fcmPushNotificationsHttpV1\Screenshot_20251021_191403.png)