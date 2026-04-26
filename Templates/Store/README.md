# Print Store eCommerce Template

## Photography Print & Product Store

eCommerce-ready template for selling photographs, prints, and products. Includes product cards and checkout interface.

### 🎨 Design Features
- **Product Cards**: Professional presentation
- **Pricing Display**: Clear pricing tiers
- **Add to Cart**: Simple purchasing
- **Store Navigation**: Category browsing
- **Professional Layout**: eCommerce-ready
- **Mobile Shop**: Responsive design

### 📋 Pages Included
- `index.html` - Store landing page
- `gallery.html` - Product gallery/shop
- `contact.html` - Support contact
- `assets/` - CSS, JavaScript, image placeholders

### 🎯 Customization Guide

#### Store Name
In `index.html`:
```html
<!-- CUSTOMIZE: Your store name -->
<h1 class="store-name">Photo Store</h1>
```

#### Logo
```html
<!-- CUSTOMIZE: Your logo -->
<img src="assets/images/logo.png" class="logo">
```

#### Product Images
In `gallery.html`:
```html
<!-- CUSTOMIZE: Your product photos -->
<img src="assets/images/product-1.jpg" class="product-image">
```

#### Product Info
```html
<!-- CUSTOMIZE: Product details -->
<div class="product">
  <h3>Limited Edition Print</h3>
  <p class="price">$49.99</p>
  <p class="description">High quality print</p>
  <button>Add to Cart</button>
</div>
```

#### Colors
In `assets/css/style.css`:
```css
:root {
  --primary: #000;
  --accent: #fff;
  --price-color: #e74c3c;
}
```

#### Categories
In `gallery.html`:
```html
<!-- CUSTOMIZE: Product categories -->
<filter>Prints</filter>
<filter>Canvas</filter>
<filter>Digital</filter>
```

#### Shipping Info
In `index.html`:
```html
<!-- CUSTOMIZE: Shipping details -->
<p>Free shipping on orders over $100</p>
```

#### Contact/Support
In `contact.html`:
```html
<!-- CUSTOMIZE: Support email -->
<form action="mailto:support@store.com">
```

### 🚀 Deployment

1. Upload product photography
2. Set up product information
3. Configure pricing
4. Add shipping details
5. Set up payment processing
6. Deploy to hosting

### 📦 Interactive Edition
See `Interactive Design/` for full eCommerce:
- Shopping cart system
- User accounts & order history
- Payment integration (Stripe/PayPal)
- Inventory management
- Admin dashboard
- Order tracking
- Digital downloads

---

**Built with Aeon WebTemplates**
