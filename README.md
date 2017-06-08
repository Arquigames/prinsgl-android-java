# prinsgl-android-java
Librería OpenGL para android-java 

Esta librería que trabaja sobre OpenGL para android java está basado en la librería gráfica THREEJS .
Vi la necesidad de migrar la librería a JAVA pero no fue posible todo debido a la complejidad pero muchas cosas es posible trabajar.
Este es un aporte libre a la comunidad, cualquier sugerencia estaré pendiente.

El paquete principal reside en com.arquigames.prinsgl.

No hay necesidad de incluir librerías externas ya que únicamente se ha incluido la librería JSON que reside en el paquete com.arquigames.extlib.json 
que es de uso público y se incluyó para el uso de la importación de modelos 3D desde blender utilizando la misma lógica de 
importación/exportación de threejs para blender con limitaciones. Ustedes pueden crear un modelo 3D y exportarlo en formato JSON de blender y 
luego cargarlo en esta librería.

Existe una prueba en el paquete test_app que pueden ejecutarlo si tienen conocimientos sobre la generación de apk en android studio.

Cabe recalcar que existe variaciones de la lógica del código de threejs para esta librería, por lo que al comparar linea por línea y lo que hace esto, de todo el código con la librería threejs existirán variaciones.
