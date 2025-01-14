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








document.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM tamamen yüklendi');
    const products = [];
    const carouselItems = document.querySelectorAll('.carousel-item');
    const productNames = new Set();

    carouselItems.forEach((carouselItem, index) => {
        console.log(`İşleniyor: Carousel Item ${index + 1}`);
        const cardDescription = carouselItem.querySelector('.card-description');
        const priceElement = carouselItem.querySelector('.price');
        const imageElement = carouselItem.querySelector('img');

        if (cardDescription && priceElement && imageElement) {
            const name = cardDescription.innerText.trim();
            if (productNames.has(name)) {
                console.log(`${name} zaten eklendi, atlanıyor`);
                return; // Aynı isimden olan ürünler atlanır
            }

            console.log(`Ürün adı: ${name}`);
            let price = parseFloat(priceElement.innerText.replace(',', '').replace('₺', '').trim());
            console.log(`Ürün fiyatı: ${price}`);
            const imageUrl = imageElement.src.trim();
            console.log(`Ürün resim URL: ${imageUrl}`);

            if (!isNaN(price)) {
                products.push({
                    name: name,
                    price: price,
                    description: "Product Description",
                    imageUrl: imageUrl,
                });
                productNames.add(name);
                console.log(`Eklendi: ${name} - ${price} - ${imageUrl}`);
            }
        } else {
            console.log('Gerekli öğeler bulunamadı');
        }
    });

    console.log('Ürünler:', products);

    if (products.length > 0) {
        fetch('/products/batch', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(products)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Başarılı:', data);
        })
        .catch((error) => {
            console.error('Hata:', error);
        });
    } else {
        console.log('Eklenecek ürün bulunamadı');
    }
});
