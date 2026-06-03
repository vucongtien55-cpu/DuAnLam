document.addEventListener('DOMContentLoaded', function () {
  const btnCreate = document.getElementById('btn-create');
  const btnCancel = document.getElementById('btn-cancel');
  const form = document.getElementById('product-form');
  const nameInput = document.getElementById('name');
  const descInput = document.getElementById('description');
  const priceInput = document.getElementById('price');
  const tbody = document.getElementById('products-tbody');

  // Load products
  function loadProducts() {
    fetch('/api/products?page=0&size=100')
      .then(r => r.json())
      .then(obj => {
        tbody.innerHTML = '';
        if (obj && obj.data && obj.data.content) {
          obj.data.content.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
              <td>${p.id || ''}</td>
              <td>${p.name}</td>
              <td>${p.price ? Number(p.price).toLocaleString('vi-VN') : '0'} ₫</td>
              <td>
                <button class="btn btn-ghost" style="padding: 4px 8px; font-size: 12px;">Sửa</button>
                <button class="btn btn-ghost" style="padding: 4px 8px; font-size: 12px;">Xóa</button>
              </td>
            `;
            tbody.appendChild(tr);
          });
        }
      })
      .catch(err => console.error('Error loading products:', err));
  }

  btnCreate.addEventListener('click', () => {
    form.style.display = 'block';
    nameInput.value = '';
    descInput.value = '';
    priceInput.value = '';
  });

  btnCancel.addEventListener('click', () => {
    form.style.display = 'none';
  });

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    // Create product via API
    fetch('/api/admin/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: nameInput.value,
        description: descInput.value,
        price: parseFloat(priceInput.value)
      })
    })
    .then(r => r.json())
    .then(obj => {
      alert('Tạo sản phẩm thành công!');
      form.style.display = 'none';
      loadProducts();
    })
    .catch(err => alert('Lỗi: ' + err.message));
  });

  loadProducts();
});

