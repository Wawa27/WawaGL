#version 460

layout(triangles) in;
layout(line_strip, max_vertices = 6) out;

in vec3 oNormal[];

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    vec3 position = gl_in[0].gl_Position.xyz;
    vec3 normal = oNormal[0].xyz;

    gl_Position = projection * view * model * (vec4(position, 1.0));
    EmitVertex();
    gl_Position = projection * view * model * (vec4(position + normal, 1.0));
    EmitVertex();
    EndPrimitive();

    position = gl_in[1].gl_Position.xyz;
    normal = oNormal[1].xyz;

    gl_Position = projection * view * model * (vec4(position, 1.0));
    EmitVertex();
    gl_Position = projection * view * model * (vec4(position + normal, 1.0));
    EmitVertex();
    EndPrimitive();

    position = gl_in[2].gl_Position.xyz;
    normal = oNormal[2].xyz;

    gl_Position = projection * view * model * (vec4(position, 1.0));
    EmitVertex();
    gl_Position = projection * view * model * (vec4(position + normal, 1.0));
    EmitVertex();
    EndPrimitive();
}
