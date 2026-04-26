/* Base Photography Template JavaScript */

// Smooth scrolling
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({ behavior: 'smooth' });
        }
    });
});

// Gallery lightbox effect
document.querySelectorAll('.gallery-item img, .preview-item img').forEach(img => {
    img.addEventListener('click', function() {
        createLightbox(this.src);
    });
});

function createLightbox(src) {
    const lightbox = document.createElement('div');
    lightbox.className = 'lightbox';
    lightbox.innerHTML = `
        <div class="lightbox-content">
            <span class="lightbox-close">&times;</span>
            <img src="${src}" alt="Lightbox">
        </div>
    `;
    document.body.appendChild(lightbox);
    
    lightbox.addEventListener('click', function(e) {
        if (e.target === this || e.target.className === 'lightbox-close') {
            this.remove();
        }
    });
}

// Add lightbox styles dynamically
const style = document.createElement('style');
style.textContent = `
    .lightbox {
        display: flex;
        align-items: center;
        justify-content: center;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.9);
        z-index: 9999;
    }
    .lightbox-content {
        position: relative;
        max-width: 90%;
        max-height: 90%;
    }
    .lightbox-content img {
        max-width: 100%;
        max-height: 80vh;
        object-fit: contain;
    }
    .lightbox-close {
        position: absolute;
        top: 20px;
        right: 30px;
        font-size: 40px;
        cursor: pointer;
        color: white;
    }
`;
document.head.appendChild(style);

// Contact form handler
const contactForm = document.querySelector('.contact-form');
if (contactForm) {
    contactForm.addEventListener('submit', function(e) {
        // Validation here if needed
        console.log('Form submitted');
    });
}

// Lazy loading images
if ('IntersectionObserver' in window) {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                if (img.dataset.src) {
                    img.src = img.dataset.src;
                    observer.unobserve(img);
                }
            }
        });
    });
    
    document.querySelectorAll('img[data-src]').forEach(img => observer.observe(img));
}

// Add hover effects to product cards and service items
document.querySelectorAll('.product-card, .service-card, .gallery-item').forEach(item => {
    item.addEventListener('mouseenter', function() {
        this.style.transform = 'translateY(-5px)';
    });
    item.addEventListener('mouseleave', function() {
        this.style.transform = 'translateY(0)';
    });
});

console.log('Photography Template initialized');
