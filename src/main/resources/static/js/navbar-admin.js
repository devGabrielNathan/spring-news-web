document.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.getElementById('adminSidebar');
    const toggleBtn = document.getElementById('sidebarToggle');
    
    setTimeout(function() {
        document.body.classList.add('transitions-ready');
    }, 100);

    if (sidebar && toggleBtn) {
        toggleBtn.addEventListener('click', function () {
            const isCollapsed = sidebar.classList.toggle('collapsed');
            localStorage.setItem('sidebar-collapsed', isCollapsed);
        });
    }
});