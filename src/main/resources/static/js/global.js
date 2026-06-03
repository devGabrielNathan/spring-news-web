document.addEventListener('DOMContentLoaded', () => {
    const body = document.body;
    const header = document.querySelector('header');

    if (!header) {
        return;
    }

    const updateNavbarState = () => {
        body.classList.toggle('navbar-scrolled', window.scrollY > 12);
    };

    const syncNavbarHeight = () => {
        body.style.setProperty('--navbar-height', `${header.offsetHeight}px`);
    };

    syncNavbarHeight();
    updateNavbarState();

    const toggleBtn = document.getElementById('navbar-toggle');
    const overlay = document.getElementById('mobile-menu-overlay');
    const mobileLinks = document.querySelectorAll('.mobile-nav-link');

    const toggleMobileNav = () => {
        body.classList.toggle('mobile-nav-active');
    };

    const closeMobileNav = ()    => {
        body.classList.remove('mobile-nav-active');
    };

    if (toggleBtn) {
        toggleBtn.addEventListener('click', toggleMobileNav);
    }

    if (overlay) {
        overlay.addEventListener('click', closeMobileNav);
    }

    mobileLinks.forEach(link => {
        link.addEventListener('click', closeMobileNav);
    });

    window.addEventListener('resize', () => {
        syncNavbarHeight();
        updateNavbarState();
        
        if (window.innerWidth > 768) {
            closeMobileNav();
        }
    });

    window.addEventListener('scroll', updateNavbarState, { passive: true });
});