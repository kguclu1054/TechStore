// EnCokSatılanlarcarousel'i için JavaScript üst
let topItems = document.querySelectorAll('#EnCokSatılanlarcarousel .carousel-item');

topItems.forEach((el) => {
    const minPerSlide = 6;
    let next = el.nextElementSibling;
    for (let i = 1; i < minPerSlide; i++) {
        if (!next) {
            next = topItems[0];
        }
        let cloneChild = next.cloneNode(true);
        el.appendChild(cloneChild.children[0]);
        next = next.nextElementSibling;
    }
});

// smartphonecarousel için JavaScript kodları
let bottomItems = document.querySelectorAll('#smartphonecarousel .carousel-item');

bottomItems.forEach((el) => {
    const minPerSlide = 6;
    let next = el.nextElementSibling;
    for (let i = 1; i < minPerSlide; i++) {
        if (!next) {
            next = bottomItems[0];
        }
        let cloneChild = next.cloneNode(true);
        el.appendChild(cloneChild.children[0]);
        next = next.nextElementSibling;
    }
});


let laptopItems = document.querySelectorAll('#laptopCarousel .carousel-item');

laptopItems.forEach((el) => {
    const minPerSlide = 6;
    let next = el.nextElementSibling;
    for (let i = 1; i < minPerSlide; i++) {
        if (!next) {
            next = laptopItems[0];
        }
        let cloneChild = next.cloneNode(true);
        el.appendChild(cloneChild.children[0]);
        next = next.nextElementSibling;
    }
});














let currentIndex = 0;  // Başlangıç index'i
const totalSlides = 7; // Slider'da toplam 7 slayt var
const transitionTime = 5000; // Otomatik geçiş süresi (5 saniye)

// Yönlendirme işlevi
function moveSlide(direction) {
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');

    // Yönü kontrol et ve currentIndex'i 1 arttır veya azalt
    currentIndex += direction;

    // Eğer currentIndex, 0'dan küçükse, ilk slayta git
    if (currentIndex < 0) {
        currentIndex = totalSlides - 1;  // Son slayta git
    }

    // Eğer currentIndex, toplam slayt sayısına eşitse, başa dön
    else if (currentIndex >= totalSlides) {
        currentIndex = 0;  // İlk slayta git
    }

    // Slaytları kaydırmak için carousel container'ını hareket ettir
    const carousel = document.querySelector('.carousel');
    carousel.style.transition = "transform 0.5s ease-in-out";  // Geçiş süresi ayarlandı
    carousel.style.transform = `translateX(-${currentIndex * 100}%)`;

    // Sağ ok tuşunu devre dışı bırak (son slayta geldiğinde)
    if (currentIndex === totalSlides - 1) {  // Son slayta geldik
        nextButton.disabled = true;  // Sağ ok tuşu devre dışı
    } else {
        nextButton.disabled = false;  // Sağ ok tuşunu etkinleştir
    }

    // Sol ok tuşunu devre dışı bırak (ilk slayta geldiğinde)
    if (currentIndex === 0) {  // İlk slayta geldik
        prevButton.disabled = true;  // Sol ok tuşu devre dışı
    } else {
        prevButton.disabled = false;  // Sol ok tuşunu etkinleştir
    }
}

// Başlangıçta tuşların durumunu ayarlayalım
document.addEventListener('DOMContentLoaded', () => {
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');

    // İlk slaytta sol ok tuşunu devre dışı bırak
    prevButton.disabled = true;

    // Başlangıçta sağ ok tuşu aktif (ilk slayta olduğumuz için)
    nextButton.disabled = false;
});

// Manuel geçiş işlemlerine butonlara tıklanması durumunda
document.querySelector('.prev').addEventListener('click', () => {
    moveSlide(0);  // Sol ok tuşu işlevi
});

document.querySelector('.next').addEventListener('click', () => {
    moveSlide(0);   // Sağ ok tuşu işlevi
});

// Otomatik geçiş işlevi
setInterval(() => {
    moveSlide(1);  // Her 3 saniyede bir sağa kaydır (1 arttır)
}, transitionTime);






// LocalStorage'a ürünleri kaydet
function saveToLocalStorage(products) {
    products.forEach((product) => {
        // Eğer ürün zaten localStorage'da varsa, ekleme
        if (!localStorage.getItem(product.name)) {
            localStorage.setItem(product.name, JSON.stringify(product));
        }
    });
}

// Carousel'den ürünleri çıkar ve ürün listesi oluştur
function extractProducts() {
    const products = [];
    const productElements = document.querySelectorAll('.carousel-item');

    console.log('Ürün Elemanları:', productElements); // Kontrol etmek için log

    productElements.forEach((element) => {
        const name = element.querySelector('.card-description').textContent.trim();
        
        // Aynı isme sahip ürünü kontrol et
        if (localStorage.getItem(name)) {
            return; // Eğer bu ürün daha önce eklenmişse, bu ürünü geç
        }

        const price = parseFloat(
            element.querySelector('.price').textContent.trim().replace('₺', '').replace('.', '').replace(',', '.')
        );
        const description = name;
        const imageUrl = element.querySelector('img').src;
        const category = "Laptop"; // Statik kategori, gerektiğinde değiştirin

        const product = { name, price, description, imageUrl, category };
        products.push(product); // Ürünü listeye ekle
    });

    console.log('Extracted Products:', products); // Ürünlerin doğru çıkarıldığını kontrol et

    saveToLocalStorage(products); // Ürünleri LocalStorage'a kaydet
    return products;
}

// Ürünleri backend'e gönder
function sendProducts() {
    const products = extractProducts(); // Ürünleri çıkar

    if (products.length > 0) {
        console.log('Gönderilecek Ürünler:', products); // Gönderilecek ürünleri kontrol et

        fetch('/products/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(products),
        })
            .then((response) => {
                console.log('Sunucu Cevabı:', response); // Sunucudan gelen cevabı kontrol et
                if (response.ok) {
                    return response.text(); // Başarı mesajını döndür
                } else {
                    throw new Error("Ürün eklenirken bir hata oluştu.");
                }
            })
            .then((message) => {
                console.log('Başarı:', message); // Başarı mesajını logla
                alert('Ürünler başarıyla eklendi!'); // Kullanıcıya başarı mesajı göster
            })
            .catch((error) => {
                console.error('Hata:', error.message); // Hata mesajını logla
                alert('Ürünler eklenirken bir hata oluştu!'); // Kullanıcıya hata mesajı göster
            });
    } else {
        console.log('Yeni ürün bulunamadı!'); // Yeni ürün bulunmazsa konsola yaz
        
    }
}

// Sayfa yüklendikten sonra ürünleri gönder
document.addEventListener('DOMContentLoaded', function() {
    sendProducts(); // Sayfa yüklendikten sonra sendProducts çalıştırılır
});








document.addEventListener('DOMContentLoaded', (event) => {
  document.querySelectorAll('.card').forEach(card => {
    card.addEventListener('click', (e) => {
      if (!e.target.closest('.tooltip-box')) {
        const productId = card.getAttribute('data-id');
        window.location.href = `http://localhost:8080/products/${productId}`;
      }
    });
  });

  document.querySelectorAll('.tooltip-box').forEach(tooltip => {
    tooltip.addEventListener('click', (e) => {
      e.stopPropagation();
      const productId = tooltip.closest('.card').getAttribute('data-id');
      const productDescription = tooltip.closest('.card').querySelector('.card-description').innerText;
      const productPrice = tooltip.closest('.card').querySelector('.price').innerText;
      const productImageUrl = tooltip.closest('.card').querySelector('.card-img img').src; // Ürün resim URL'sini alıyoruz
      addToCart(productId, productDescription, productPrice, productImageUrl);
    });
  });
});

function addToCart(productId, productDescription, productPrice, productImageUrl) {
    console.log("Adding to cart product id: " + productId);

    // Ürünü local storage'a ekleyin
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.push({ id: productId, description: productDescription, price: productPrice, imageUrl: productImageUrl });
    localStorage.setItem('cart', JSON.stringify(cart));

    alert('Ürün sepete eklendi!');
}

function redirectToProductPage(productId) {
    window.location.href = `/products/${productId}`;
}

// Sayfa yüklendiğinde fonksiyonu çağır
window.onload = function() {
    const productId = new URLSearchParams(window.location.search).get('id');
    if (productId) {
        fetchProductDetails(productId);
    }
};

function fetchProductDetails(productId) {
    fetch(`/api/products/${productId}`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                document.getElementById('productName').innerText = data.name;
                document.getElementById('productImage').src = data.imageUrl;
                document.getElementById('productDescription').innerText = data.description;
                document.getElementById('productPrice').innerText = 'Fiyat: ₺' + data.price;
            } else {
                console.error('Ürün bulunamadı');
            }
        })
        .catch(error => console.error('Hata:', error));
}




document.addEventListener('DOMContentLoaded', () => {
	       fetch('/auth/userInfo')
	           .then(response => {
	               if (!response.ok) {
	                   throw new Error("Kullanıcı bilgisi alınamadı");
	               }
	               return response.text();
	           })
	           .then(email => {
	               if (email) {
	                   document.getElementById('userEmail').textContent = email;
	               } else {
	                   document.getElementById('userEmail').textContent = "Giriş yapınız";
	               }
	           })
	           .catch(error => {
	               console.error("Hata:", error);
	               document.getElementById('userEmail').textContent = "Giriş yapınız";
	           });
	   });