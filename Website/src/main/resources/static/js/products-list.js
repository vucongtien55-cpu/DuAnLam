document.addEventListener('DOMContentLoaded', function () {
  let currentPage = 0;
  let currentCategory = null;
  let currentKeyword = null;

  const searchInput = document.getElementById('search-input');
  const categoryFilter = document.getElementById('category-filter');
  const productsList = document.getElementById('products-list');
  const paginationDiv = document.getElementById('pagination');
  const errorMsg = document.getElementById('error-msg');

  // Load categories
  fetch('/api/categories')
    .then(r => r.json())
    .then(obj => {
      if (obj && obj.data && Array.isArray(obj.data)) {
        obj.data.forEach(cat => {
          const option = document.createElement('option');
          option.value = cat.id;
          option.textContent = cat.name;
          categoryFilter.appendChild(option);
        });
      }
    })
    .catch(err => console.error('Error loading categories:', err));

  // Load products
  function loadProducts() {
    let url = '/api/products?page=' + currentPage + '&size=12';

    if (currentKeyword) {
      url = '/api/products/search?keyword=' + encodeURIComponent(currentKeyword) + '&page=' + currentPage + '&size=12';
    } else if (currentCategory) {
      url = '/api/products/category/' + currentCategory + '?page=' + currentPage + '&size=12';
    }

    fetch(url)
      .then(r => r.json())
      .then(obj => {
        if (obj && obj.data) {
          const pageData = obj.data;
          productsList.innerHTML = '';

          const items = pageData.content || [];
          items.forEach(p => {
            const card = document.createElement('article');
            card.className = 'card';

            const thumb = document.createElement('div');
            thumb.className = 'thumb';
            if (p.images && p.images.length) {
              const img = document.createElement('img');
              img.src = p.images[0];
              img.alt = p.name;
              img.loading = 'lazy';
              img.style.width = '100%';
              img.style.height = '150px';
              img.style.objectFit = 'cover';
              thumb.appendChild(img);
            } else {
              thumb.textContent = 'Hình';
            }

            const title = document.createElement('h3');
            title.textContent = p.name;

            const desc = document.createElement('p');
            desc.textContent = p.shortDescription || '';

            const price = document.createElement('div');
            price.className = 'price';
            price.textContent = p.price ? Number(p.price).toLocaleString('vi-VN') + ' ₫' : '';

            const link = document.createElement('a');
            link.href = '/products/' + p.slug;
            link.className = 'btn btn-primary';
            link.textContent = 'Xem chi tiết';

            card.appendChild(thumb);
            card.appendChild(title);
            card.appendChild(desc);
            card.appendChild(price);
            card.appendChild(link);
            productsList.appendChild(card);
          });

          // Pagination
          paginationDiv.innerHTML = '';
          if (pageData.totalPages > 1) {
            for (let i = 0; i < pageData.totalPages; i++) {
              const btn = document.createElement('button');
              btn.className = 'pagination-btn' + (i === currentPage ? ' active' : '');
              btn.textContent = i + 1;
              btn.onclick = () => {
                currentPage = i;
                loadProducts();
                window.scrollTo(0, 0);
              };
              paginationDiv.appendChild(btn);
            }
          }
        }
      })
      .catch(err => {
        errorMsg.textContent = 'Lỗi khi tải sản phẩm: ' + err.message;
        errorMsg.style.display = 'block';
        console.error(err);
      });
  }

  // Event listeners
  searchInput.addEventListener('keyup', function (e) {
    if (e.key === 'Enter') {
      currentKeyword = searchInput.value;
      currentPage = 0;
      loadProducts();
    }
  });

  categoryFilter.addEventListener('change', function () {
    currentCategory = this.value ? parseInt(this.value) : null;
    currentKeyword = null;
    currentPage = 0;
    loadProducts();
  });

  // Initial load
  loadProducts();
});

