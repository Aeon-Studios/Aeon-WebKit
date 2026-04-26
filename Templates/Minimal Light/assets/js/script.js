/* Photography Template JavaScript - Shared */

document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) target.scrollIntoView({ behavior: 'smooth' });
    });
});

// Lightbox
document.querySelectorAll('.gallery-item img, .preview-item img').forEach(img => {
    img.addEventListener('click', function() {
        const lightbox = document.createElement('div');
        lightbox.className = 'lightbox';
        lightbox.innerHTML = `<div class="lightbox-content"><span class="close">&times;</span><img src="${this.src}"></div>`;
        document.body.appendChild(lightbox);
        lightbox.onclick = (e) => e.target === lightbox && lightbox.remove();
    });
});
