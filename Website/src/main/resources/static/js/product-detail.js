document.addEventListener('DOMContentLoaded', function () {
  const slug = window.location.pathname.split('/').pop();
  const detailDiv = document.getElementById('product-detail');
  const errorMsg = document.getElementById('error-msg');

  fetch('/api/products/' + slug)
    .then(r => r.json())
    .then(obj => {
      if (obj && obj.data) {
        const p = obj.data;

        const html = `
          <div class="detail-grid">
            <div class="detail-gallery">
              ${p.images && p.images.length ?
                `<img src="${p.images[0]}" alt="${p.name}" style="width:100%;max-width:500px;border-radius:12px;">`
                : `<div style="height:500px;background:#f1f5f9;display:flex;align-items:center;justify-content:center;border-radius:12px;color:#6b7280;font-weight:600;">Hình sản phẩm</div>`
              }
            </div>
            <div class="detail-info">
              <h1 style="margin:0 0 12px;font-size:28px;">${p.name}</h1>
              <p style="color:#6b7280;margin:0 0 20px;font-size:16px;">${p.shortDescription || ''}</p>

              <div style="background:#f8fafc;padding:16px;border-radius:12px;margin-bottom:20px;">
                <p style="color:#6b7280;margin:0 0 8px;font-size:13px;text-transform:uppercase;font-weight:600;">Giá</p>
                <p style="margin:0;font-size:32px;color:#1e6fb8;font-weight:700;">
                  ${p.price ? Number(p.price).toLocaleString('vi-VN') + ' ₫' : 'Liên hệ'}
                </p>
              </div>

              <div style="display:flex;gap:12px;margin-bottom:24px;">
                <button class="btn btn-primary" style="flex:1;">Thêm vào giỏ hàng</button>
                <button class="btn btn-outline" style="flex:1;">Mua ngay</button>
              </div>

              <div style="background:#fffbf0;border-left:4px solid #e74c3c;padding:12px;border-radius:6px;margin-bottom:24px;">
                <p style="margin:0;color:#e74c3c;font-size:13px;font-weight:600;">⚡ ĐẶC BẢO HÀNH TRỊ GIÁ</p>
                <p style="margin:6px 0 0;color:#6b7280;font-size:13px;">Hỗ trợ bảo hành chính hãng 12 tháng</p>
              </div>

              <div style="border-top:1px solid rgba(15,23,36,0.06);padding-top:16px;">
                <p style="color:#6b7280;margin:0;font-size:13px;"><strong>SKU:</strong> ${p.id || 'N/A'}</p>
                <p style="color:#6b7280;margin:8px 0 0;font-size:13px;"><strong>Danh mục:</strong> ${p.category?.name || 'Không'}</p>
              </div>
            </div>
          </div>

          <div style="margin-top:40px;padding-top:30px;border-top:2px solid #f0f4f8;">
            <h2 style="margin:0 0 16px;font-size:22px;">Mô tả chi tiết</h2>
            <div style="color:#6b7280;line-height:1.8;max-width:900px;">
              ${p.description ? '<p>' + p.description.replace(/\n/g, '</p><p>') + '</p>' : '<p>Chưa có mô tả chi tiết</p>'}
            </div>
          </div>
        `;

        detailDiv.innerHTML = html;
      } else {
        throw new Error('Không tìm thấy sản phẩm');
      }
    })
    .catch(err => {
      errorMsg.textContent = 'Lỗi: ' + err.message;
      errorMsg.style.display = 'block';
      console.error(err);
    });
});



