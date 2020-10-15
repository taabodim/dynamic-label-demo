<template>
  <footer class="footer">
    <div class="container-fluid">
      <ul class="nav">
        <li class="nav-item">
          <a
            href="http://mediamath.com"
            target="_blank"
            rel="noopener"
            class="nav-link"
          >
          Mediamath
          </a>
        </li>
        <li class="nav-item">
          <a
            href="https://www.mediamath.com/about-us/"
            target="_blank"
            rel="noopener"
            class="nav-link"
          >
            About Us
          </a>
        </li>
        <li class="nav-item">
          <a
            href="https://www.mediamath.com/blog/"
            target="_blank"
            rel="noopener"
            class="nav-link"
          >
            Blog
          </a>
        </li>
      </ul>
      <div class="copyright">
        &copy; SharkWeek {{ year }}, made with <i class="tim-icons icon-heart-2"></i> by
        <a
          href="https://github.com/taabodim/dynamic-label-demo"
          target="_blank"
          rel="noopener"
          >Mahmoud Taabodi and Yang Yang</a
        >
        for a better mediamath.
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style></style>
