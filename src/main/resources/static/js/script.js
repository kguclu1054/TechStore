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








function saveToLocalStorage(products) {
    products.forEach((product) => {
        // Ürünlerin adını kontrol ederek benzersiz olup olmadığını kontrol edin
        if (!localStorage.getItem(product.name)) {
            localStorage.setItem(product.name, JSON.stringify(product));
        }
    });
}

function extractProducts() {
    const products = [];
    const productElements = document.querySelectorAll('.carousel-item');
    
    productElements.forEach((element) => {
        const name = element.querySelector('.card-description').textContent.trim();
        const price = parseFloat(element.querySelector('.price').textContent.trim().replace('₺', '').replace('.', '').replace(',', '.'));
        const description = element.querySelector('.card-description').textContent.trim();
        const imageUrl = element.querySelector('img').src;
        const category = "Laptop";  // Kategoriyi dinamik olarak değiştirebilirsiniz

        const product = {
            name,
            price,
            description,
            imageUrl,
            category
        };

        // Aynı isimde ürün olup olmadığını kontrol et
        if (!localStorage.getItem(name)) {
            products.push(product);
        }
    });

    saveToLocalStorage(products); // Ürünleri yerel depoya kaydet

    return products;
}

function sendProducts() {
    const products = extractProducts();

    if (products.length > 0) {
        fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(products),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Başarıyla gönderildi:', data);
        })
        .catch((error) => {
            console.error('Hata:', error);
        });
    } else {
        console.log('Gönderilecek yeni ürün yok.');
    }
}

// Sayfa yüklendiğinde `sendProducts` fonksiyonunu çağır
window.onload = sendProducts;






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


function redirectToProductPage(productId) {
    window.location.href = `/products/${productId}`;
}







