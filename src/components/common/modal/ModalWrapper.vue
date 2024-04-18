<script setup>
  import CustomButton from "../CustomButton.vue";
  import useModalStore from "../../../stores/modal";
  import { onMounted, ref } from "vue";
  import { storeToRefs } from "pinia";
  const store = useModalStore();
  const { closeModal, openModal, confirm, modalInfo } = store;

  const state = storeToRefs(store);
  const submit = () => state.modalInfo.value.confirm();
  const isInput = ref(false);

  onMounted(() => {
    const modalNode = document.querySelector("#modal");
    /** 첫 번째 INPUT 태그를 포커스 하도록 */
    if (modalNode) modalNode.querySelector("input")?.focus();
    if (modalNode.querySelectorAll("input").length > 1) {
      isInput.value = true;
    }
  });
</script>

<template>
  <div :class="$style.modalWrapper" @keydown.esc="closeModal">
    <div :class="$style.modal" id="modal">
      <slot />
      <div :class="$style.utilSection">
        <div :class="$style.buttonSection">
          <CustomButton
            @click="submit"
            :style="[
              !isInput
                ? 'background-color: rgb(49, 129, 246)'
                : modalInfo.firstValue.length && modalInfo.twiceValue.length
                  ? { 'background-color': 'rgb(49, 129, 246)' }
                  : { filter: 'grayscale(100%)', opacity: '40%' },
            ]"
            :disabled="
              isInput &&
              !(modalInfo.firstValue.length && modalInfo.twiceValue.length)
            "
            >확인하기</CustomButton
          >
          <CustomButton @click="closeModal">취소하기</CustomButton>
        </div>
      </div>
    </div>
  </div>
</template>

<style module>
  @keyframes openEffect {
    from {
      transform: translate(-50%, -50%) scale(95%);
    }
    to {
      transform: translate(-50%, -50%) scale(100%);
    }
  }

  .modal {
    animation: openEffect 0.3s;

    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 10002;
    background: white;
    min-width: 32rem;
    min-height: 10rem;
    height: auto;
    border-radius: 0.7rem;
    box-sizing: border-box;

    display: flex;
    flex-direction: column;
    row-gap: 1.5rem;
    align-items: center;
    padding: 2rem;
  }

  .modalWrapper {
    position: fixed;
    width: 100%;
    height: 100%;
    background: rgba(15, 15, 15, 0.921);
    z-index: 10001;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  .utilSection {
    display: flex;
    width: 100%;
  }

  .buttonSection {
    display: flex;
    column-gap: 1rem;
    margin-left: auto;
    margin-top: 0.2rem;
  }
</style>
