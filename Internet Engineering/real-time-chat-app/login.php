<?php
    include_once "header.php";
?>
    <body>
        <div class="wrapper">
          <section class="form login">
            <header>Realtime Chat App</header>
            <form action="#">
                <div class="error-text">This is an error message</div>
                <div class="field input">
                    <label>Email</label>
                    <input type="text" name="email" placeholder="Email">
                </div>
                <div class="field input">
                    <label>Password</label>
                    <i class="fas fa-eye"></i>
                    <input type="password" name="password" placeholder="Password">
                </div>
                <div class="field button">
                    <input type="submit"  value="Continue to Chat">
                </div>
            </form>
            <div class="link">Not yet signed up? <a href="index.php">Signup now</a></div>
          </section>
        </div>
        <script src="JS/pass-show-hide.js"></script>
        <script src="JS/login.js"></script>

    </body>
</html>