import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import svgLoader from "vite-svg-loader";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx(), svgLoader()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  build: {
    /** build target directory */
    outDir: "../main/resources/static",
  },
  server: {
    proxy: {
      '/api': {
        // 프록시가 적용될 요청 경로의 시작 부분. 클라이언트가 보낸 요청의 URL이 api로 시작되면 이 설정이 적용된다. 
        target: 'http://localhost:12006/', 
        // 사용할 요청 도메인을 설정한다.        
        changeOrigin: true, 
        // HTTP 요청 헤더의 Host 값을 서버의 호스트와 일치하도록 변경한다. 이를 통해 클라이언트의 요청을 target에 설정된 도메인에서 온 것 처럼 변경할 수 있다.
        rewrite: path => path.replace(/^\/api/, ''), 
        // 프록시 요청의 경로를 재작성하는 함수를 설정한다.
      },
    },
  },
});
