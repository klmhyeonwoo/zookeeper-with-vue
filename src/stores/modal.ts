import { useApiRegisterCluster } from "../hooks/api/sidepanel/useApiRegisterCluster";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useModalStore = defineStore("modal", () => {
  const doubleInputModalState = ref(false);
  const warningModalState = ref(false);
  const modalInfo = ref({
    firstValue: "",
    firstLabel: "",
    firstPlaceholder: "",
    twiceValue: "",
    twiceLabel: "",
    twicePlaceholder: "",
    clusterAddNodeAddress: "",
    clusterAddNodeValue: "",
    clusterCurNodeValue: "",
    confirm: () => {},
  });

  const registerCluster = useApiRegisterCluster();

  const openModal = (feature: string) => {
    switch (feature) {
      case "addCluster":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "클러스터 주소"),
          (modalInfo.value.firstPlaceholder = "클러스터 주소를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "클러스터 이름"),
          (modalInfo.value.twicePlaceholder = "클러스터 이름을 입력해주세요");
        modalInfo.value.confirm = () => {
          registerCluster.mutate({
            address: modalInfo.value.firstValue,
            name: modalInfo.value.twiceValue,
          });
        };
        doubleInputModalState.value = true;
        break;
      case "addNodeTree":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "노드 경로"),
          (modalInfo.value.firstPlaceholder = "노드 경로를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "노드 값"),
          (modalInfo.value.twicePlaceholder =
            "노드에 추가하고자 하는 값을 입력해주세요");
        doubleInputModalState.value = true;
        break;
      case "duplicateNodeData":
          modalInfo.value.firstValue = "-",
          modalInfo.value.twiceValue = "-",
          modalInfo.value.confirm = () => {
            alert("중복되었습니다!");
          }
          warningModalState.value = true;
        break;
      default:
        break;
    }
    document.body.style.overflow = "hidden";
  };

  const closeModal = () => {
    const checkerOpenModal = () => {
      const checkerList = [doubleInputModalState, warningModalState];
      checkerList.forEach((item) => {
        item.value = false;
      })
    }
    if (modalInfo.value.firstValue || modalInfo.value.twiceValue) {
      if (
        window.confirm(
          `변경 사항이 저장되지 않을 수 있습니다, 정말 떠나시겠습니까?`,
        )
      ) {
        checkerOpenModal();
        document.body.style.overflow = "auto";
      }
    } else {
      checkerOpenModal();
      document.body.style.overflow = "auto";
    }
  };

  return { doubleInputModalState, warningModalState ,openModal, closeModal, confirm, modalInfo };
});

export default useModalStore;
