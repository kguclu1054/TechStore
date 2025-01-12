let items = document.querySelectorAll('.carousel .carousel-item');

items.forEach((el) => {
    const minPerSlide = 6;
	const incrementPerSlide = 2;  // Kaydırıldıkça eklenecek öğe sayısı
    let next = el.nextElementSibling;
    for (let i = 1; i < minPerSlide; i++) {
        if (!next) {
            next = items[0];
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





// Sepete ekleme butonları için event listener
document.querySelectorAll('.add-to-cart').forEach(button => {
  button.addEventListener('click', function() {
    const productId = this.getAttribute('data-product-id'); // 'this' burada tıklanan div'i ifade eder
    addToCart(productId); // Sepete ekleme fonksiyonunu çağırıyoruz
  });
});

function addToCart(productId) {
  // Sepete ürün ekleme isteği gönderme
  fetch('/api/cart/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ productId: productId })
  })
  .then(response => response.json())
  .then(data => {
    alert('Ürün sepete eklendi!');
    updateCart(); // Sepeti güncelle
  })
  .catch(error => console.error('Hata:', error));
}

// Sepetteki ürünleri güncelle
function updateCart() {
  fetch('/api/cart')
    .then(response => response.json())
    .then(cart => {
      const cartContainer = document.getElementById('cart-items');
      cartContainer.innerHTML = ''; // Önceki içeriği temizle
      cart.items.forEach(item => {
        const productDiv = document.createElement('div');
        productDiv.textContent = `Ürün: ${item.name}, Adet: ${item.quantity}`;
        cartContainer.appendChild(productDiv);
      });
    });
}