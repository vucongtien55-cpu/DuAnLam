document.addEventListener('DOMContentLoaded', function () {
  // set year in footer
  const yearEl = document.getElementById('year');
  if (yearEl) yearEl.textContent = new Date().getFullYear();

  // Mobile nav toggle
  const mobileToggle = document.getElementById('mobile-toggle');
  const mainNav = document.getElementById('main-nav');
  if (mobileToggle && mainNav) {
    mobileToggle.addEventListener('click', function () {
      const open = mainNav.classList.toggle('open');
      mobileToggle.setAttribute('aria-expanded', open ? 'true' : 'false');
    });
  }

  // Utility: format price
  function formatPrice(v) {
    if (v == null) return '';
    try { return Number(v).toLocaleString('vi-VN') + ' ₫'; } catch(e) { return v; }
  }

  // Populate featured products by calling backend API if available
  const grid = document.getElementById('products-grid');
  if (!grid) return;

  fetch('/api/products/featured')
    .then(res => res.json())
    .then(obj => {
      const items = obj && obj.data ? obj.data : [];
      if (!Array.isArray(items) || items.length === 0) {
        grid.innerHTML = '<p>Chưa có sản phẩm nổi bật.</p>';
        return;
      }

      grid.innerHTML = '';
      items.forEach(p => {
        const card = document.createElement('article');
        card.className = 'card';

        const thumb = document.createElement('div');
        thumb.className = 'thumb';
        // If product has images array or imageUrl, use it
        if (p.images && p.images.length) {
          const img = document.createElement('img');
          img.src = p.images[0];
          img.alt = p.name || 'Sản phẩm';
          img.loading = 'lazy';
          img.style.width = '100%';
          img.style.height = '150px';
          img.style.objectFit = 'cover';
          img.style.borderRadius = '8px';
          thumb.textContent = '';
          thumb.appendChild(img);
        } else if (p.imageUrl) {
          const img = document.createElement('img');
          img.src = p.imageUrl;
          img.alt = p.name || 'Sản phẩm';
          img.loading = 'lazy';
          img.style.width = '100%';
          img.style.height = '150px';
          img.style.objectFit = 'cover';
          img.style.borderRadius = '8px';
          thumb.textContent = '';
          thumb.appendChild(img);
        } else {
          thumb.textContent = 'Hình';
        }

        const title = document.createElement('h3');
        title.textContent = p.name || p.title || 'Sản phẩm';

        const desc = document.createElement('p');
        desc.textContent = p.shortDescription || p.description || '';

        const meta = document.createElement('div');
        meta.className = 'meta';

        const price = document.createElement('div');
        price.className = 'price';
        price.textContent = p.price ? formatPrice(p.price) : '';

        const link = document.createElement('a');
        link.href = '/products/' + (p.slug || '');
        link.className = 'btn btn-primary';
        link.textContent = 'Xem chi tiết';

        meta.appendChild(price);
        meta.appendChild(link);

        card.appendChild(thumb);
        card.appendChild(title);
        card.appendChild(desc);
        card.appendChild(meta);

        grid.appendChild(card);
      });
    })
    .catch(err => {
      console.info('Không thể lấy sản phẩm nổi bật:', err);
      grid.innerHTML = '<p>Không thể tải sản phẩm. Vui lòng thử lại sau.</p>';
    });

  const form = document.getElementById('contact-form');
  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      const feedback = document.getElementById('contact-feedback');

      const name = document.getElementById('name').value;
      const email = document.getElementById('email').value;
      const phone = document.getElementById('phone').value;
      const company = document.getElementById('company').value;
      const subject = document.getElementById('subject').value;
      const message = document.getElementById('message').value;

      // Send to backend API
      const params = new URLSearchParams();
      params.append('fullName', name);
      params.append('email', email);
      params.append('phone', phone || '');
      params.append('company', company || '');
      params.append('subject', subject || '');
      params.append('message', message);

      fetch('/api/contact', {
        method: 'POST',
        body: params
      })
      .then(r => r.json())
      .then(obj => {
        if (obj.success) {
          feedback.textContent = '✅ ' + (obj.message || 'Cám ơn! Chúng tôi đã nhận được yêu cầu của bạn.');
          feedback.style.color = 'green';
          form.reset();
          // Auto clear after 3s
          setTimeout(() => { feedback.textContent = ''; }, 3000);
        } else {
          feedback.textContent = '❌ ' + (obj.message || 'Có lỗi xảy ra!');
          feedback.style.color = 'red';
        }
      })
      .catch(err => {
        feedback.textContent = '❌ Lỗi kết nối: ' + err.message;
        feedback.style.color = 'red';
      });
    });
  }

  // Header search with autocomplete
  const headerSearch = document.getElementById('header-search');
  const searchSuggestions = document.getElementById('search-suggestions');

  if (headerSearch) {
    let searchTimeout;
    headerSearch.addEventListener('input', function () {
      clearTimeout(searchTimeout);
      const keyword = this.value.trim();

      if (keyword.length < 2) {
        searchSuggestions.innerHTML = '';
        searchSuggestions.classList.remove('open');
        return;
      }

      searchTimeout = setTimeout(() => {
        fetch('/api/products/search?keyword=' + encodeURIComponent(keyword) + '&page=0&size=5')
          .then(r => r.json())
          .then(obj => {
            searchSuggestions.innerHTML = '';
            if (obj && obj.data && obj.data.content) {
              obj.data.content.forEach(p => {
                const item = document.createElement('a');
                item.href = '/products/' + p.slug;
                item.className = 'item';
                item.textContent = p.name;
                searchSuggestions.appendChild(item);
              });
              if (obj.data.content.length > 0) {
                searchSuggestions.classList.add('open');
              }
            }
          })
          .catch(err => console.error('Search error:', err));
      }, 300);
    });

    // Close suggestions when click outside
    document.addEventListener('click', (e) => {
      if (!headerSearch.contains(e.target) && !searchSuggestions.contains(e.target)) {
        searchSuggestions.classList.remove('open');
      }
    });
  }
});
