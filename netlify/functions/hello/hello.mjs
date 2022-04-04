// NOTE: When using Netlify prod or dev, it's going to bundle the functions
//       with esbuild unless otherwise specified
//       https://docs.netlify.com/functions/optional-configuration/?fn-language=js#bundle-2
import { loadFile } from "nbb"

export async function handler(event, context) {
	// Ideally this would live outside out of here but in practice, changing the
	// cljs file requires also changing this file to trigger a rebuild
	const { handler } = await loadFile("./netlify/functions/hello/hello.cljs")

	return await handler(event, context)
}
