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

    window.addEventListener('resize', () => {
        syncNavbarHeight();
        updateNavbarState();
    });

    window.addEventListener('scroll', updateNavbarState, { passive: true });
});