<script setup>
  import ModalWrapper from "./ModalWrapper.vue";
  import CustomInput from "../CustomInput.vue";
  import { ref } from "vue";
  import useModalStore from "../../../stores/modal";
  import { storeToRefs } from "pinia";
  import renameArrowIcon from "../../../assets/renameArrowIcon.png";
  import modal from "../../../stores/modal";

  const store = useModalStore();
  const { modalInfo } = storeToRefs(store);
</script>

<template>
  <ModalWrapper>
    <div :class="$style.description">
      <div :class="$style.title">
        등록하고자 하는 노드가 중복으로 존재합니다
      </div>
      <span> 현재 값에서 변경을 하시고 싶다면 확인을 눌러주세요 </span>
      <img
        :src="renameArrowIcon"
        alt="중복을 표시하는 아이콘"
        :class="$style.modalImage"
      />
    </div>
    <div :class="$style.values">
      <div>
        <span :class="$style.valueState">(현재)</span>
        <template v-if="!modalInfo.clusterCurNodeValue.length">
          <span> - </span>
        </template>
        <template v-else>
          {{ modalInfo.clusterCurNodeValue }}
        </template>
      </div>
      <div>
        <span :class="$style.valueState">(변경)</span>
        <template v-if="!modalInfo.clusterAddNodeValue.length">
          <span> - </span>
        </template>
        <template v-else>
          {{ modalInfo.clusterAddNodeValue }}
        </template>
      </div>
    </div>
    <hr :class="$style.splitLine" />
  </ModalWrapper>
</template>

<style module>
  .description {
    display: flex;
    flex-direction: column;
    row-gap: 0.5rem;
    justify-content: center;
    align-items: center;
  }

  .description:nth-of-type(1) {
    font-size: 0.9rem;
    color: rgba(157, 157, 157, 0.67);
  }

  .title {
    font-size: 1.3rem;
    font-weight: 600;
    color: #212529;
  }

  .values {
    display: flex;
    flex-direction: column;
    width: 100%;
    column-gap: 1.3rem;
    align-items: center;
    justify-content: center;
    text-align: left;
    row-gap: 1rem;
  }

  .modalImage {
    width: 6rem;
    height: auto;
    margin-top: 1rem;
    margin-bottom: 1rem;
  }

  .values > div {
    border-radius: 0.4rem;
    font-size: 1.1rem;
    border: none;
    box-shadow: inset 0 0 0 1px rgba(0, 27, 55, 0.1);
    padding: 0.8rem 1.3rem 0.8rem 1.3rem;
    width: 100%;
    max-width: 20rem;
    border-radius: 50px;
    display: flex;
    align-items: center;
  }

  .valueState {
    font-size: 0.7rem;
    margin-right: 0.5rem;
    position: relative;
    top: 1px;
  }

  .splitLine {
    width: 100%;
    height: 1px;
    background: rgba(222, 222, 222, 0.56);
    border: none;
  }
</style>
