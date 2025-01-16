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








window.addEventListener('load', () => {
    const carouselItems = document.querySelectorAll('.carousel-item');
    const products = [];

    carouselItems.forEach(item => {
        const nameElement = item.querySelector('.card-description');
        const priceElement = item.querySelector('.price');
        const imageElement = item.querySelector('img');

        if (nameElement && priceElement && imageElement) {
            const name = nameElement.innerText;
            const price = priceElement.innerText;
            const imageUrl = imageElement.src;

            const product = {
                name: name,
                description: "Açıklama burada yer alacak",
                price: parseFloat(price.replace('₺', '').replace('.', '').replace(',', '.')),
                imageUrl: imageUrl
            };

            products.push(product);
        }
    });

    // Verilerin gönderilip gönderilmediğini kontrol et
    if (!localStorage.getItem('productsSent')) {
        fetch('/api/encoksatilanlar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(products)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Veriler başarıyla gönderildi:', data);
            localStorage.setItem('productsSent', 'true'); // Gönderildiği işaretle

            // Başarılı bir şekilde veri gönderildikten sonra DOM'u güncelle
            products.forEach(product => {
                const newCard = document.createElement('div');
                newCard.classList.add('carousel-item');
                newCard.innerHTML = `
                    <div class="col-md-2">
                        <div class="card">
                            <div class="card-img">
                                <img src="${product.imageUrl}" class="img-fluid" alt="${product.name}">
                                <div class="card-description">${product.name}</div>
                                <p class="price">₺${product.price.toFixed(2)}</p>
                                <div class="tooltip-box">Sepete ekle</div>
                            </div>
                        </div>
                    </div>
                `;
                console.log('Yeni kart eklendi:', newCard); // Eklemeden önce kartı logla
                document.querySelector('.carousel-inner').appendChild(newCard);
            });
        })
        .catch(error => {
            console.error('Hata:', error);
            alert('Veri gönderimi sırasında bir hata oluştu. Lütfen tekrar deneyin.');
        });
    } else {
        console.log('Veriler zaten gönderildi.');
    }
});
