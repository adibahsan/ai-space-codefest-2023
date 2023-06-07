export function scrollToId(id: String, blockType: ScrollLogicalPosition = "end", delay = 250) {
    setTimeout(() => {
        const targetObject = document.querySelector(`#${id}`)
        console.log(`scrolling to id = ${id} - blockType - ${blockType} with targetObject ${targetObject}`)
        console.log("targetObject",targetObject)
        targetObject?.scrollIntoView({behavior: "smooth", block: blockType})
    }, delay)
}
